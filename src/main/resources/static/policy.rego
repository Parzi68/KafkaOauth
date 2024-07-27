package kafka.opa

import { allow, deny } from 'rego'

// Define a function to check if a user is authorized
func isAuthorized(user: string) {
  // For now, let's just allow access for users with the "kafka-client-producer" role
  return user == "kafka-client-producer"
}

// Define the policy for Kafka resources
policy = {
  // Allow access for authorized users
  allow[resource] := isAuthorized(resource.user)
}
