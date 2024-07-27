package kafka.authz

import future.keywords.in
import rego.v1

# By default, deny requests
default allow := false

# Allow the action if the user is granted permission to perform the action and the JWT is valid.
allow if {
    some permission
    user_is_granted[permission]

    # Check if the permission permits the action.
    input.action == permission.action
    input.topic == permission.topic

    # Ensure the JWT is valid
    valid_jwt
}

# Helper rule to extract the payload from the JWT
payload := decode_jwt(input.jwt)

# Decode the JWT and extract the payload
decode_jwt(jwt) := payload if {
    [_, payload, _] := io.jwt.decode(jwt)
}

# Ensure the JWT is valid
valid_jwt if {
    not token_expired(payload.exp)
    payload.aud == "your-expected-audience"
}

# Check if the token is expired
token_expired(exp) = true if {
    now := time.now_ns() / 1000000000
    exp < now
}

# user_is_granted is a set of permissions for the user identified in the request.
user_is_granted[permission] if {
    some i, j

    # `role` assigned an element of the user_roles for this user...
    role := payload.realm_access.roles[i]

    # `permission` assigned a single permission from the permissions list for 'role'...
    permission := role_permissions[role][j]
}

# Data structure for role permissions
# This would typically be defined in your input data, but for illustration, we can define it here.
# In a real use case, this should be provided in the data input or an external source.
role_permissions := {
    "kafka-client-producer": [{"action": "produce", "topic": "myTopic"}],
    "EMS": [{"action": "consume", "topic": "myTopic"}],
    "Admin": [
        {"action": "produce", "topic": "myTopic"},
        {"action": "consume", "topic": "myTopic"},
    ],
}

#-----------------------------------------------------------------------------
# Helpers for processing Kafka operation input. This logic could be split out
# into a separate file and shared. For conciseness, we have kept it all in one
# place.
#-----------------------------------------------------------------------------

is_write_operation if {
    input.action.operation == "WRITE"
}

is_read_operation if {
    input.action.operation == "READ"
}

is_topic_resource if {
    input.action.resourcePattern.resourceType == "TOPIC"
}

topic_name := input.action.resourcePattern.name if {
    is_topic_resource
}

principal := {"fqn": parsed.CN, "name": cn_parts[0]} if {
    parsed := parse_user(input.requestContext.principal.name)
    cn_parts := split(parsed.CN, ".")
}

# If client certificates aren't used for authentication
else := {"fqn": "", "name": input.requestContext.principal.name}

parse_user(user) := {key: value |
    parts := split(user, ",")
    [key, value] := split(parts[_], "=")
}
