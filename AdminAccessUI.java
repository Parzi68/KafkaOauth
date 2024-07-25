// src/main/java/com/example/kafkaoauth/AdminAccessUI.java
package com.example.kafkaoauth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route
public class AdminAccessUI extends VerticalLayout {

    private Grid<Action> grid;
    private ListDataProvider<Action> dataProvider;
    private List<Action> actions = new ArrayList<>();

    public AdminAccessUI() {
        actions.add(new Action("Produce", true));
        actions.add(new Action("Consume", true));

        grid = new Grid<>();
        grid.addColumn(Action::getName).setHeader("Action");
        grid.addColumn(Action::getGranted).setHeader("Granted");

        dataProvider = DataProvider.ofCollection(actions);
        grid.setDataProvider(dataProvider);

        Button saveButton = new Button("Save Changes");
        saveButton.addClickListener(e -> saveChanges());

        add(grid, saveButton);
    }

    private void saveChanges() {
        // TO DO: implement saving changes to the policy
        System.out.println("Changes saved!");
    }

    public static class Action {
        private String name;
        private boolean granted;

        public Action(String name, boolean granted) {
            this.name = name;
            this.granted = granted;
        }

        public String getName() {
            return name;
        }

        public boolean getGranted() {
            return granted;
        }
    }
}
