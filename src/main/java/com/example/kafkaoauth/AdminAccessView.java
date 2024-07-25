// src/main/java/com/example/kafkaoauth/AdminAccessView.java
package com.example.kafkaoauth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route
public class AdminAccessView extends VerticalLayout {
    private Grid<Action> grid;
    private ListDataProvider<Action> dataProvider;
    private List<Action> actions = new ArrayList<>();

    public AdminAccessView() {
        actions.add(new Action("Produce", true));
        actions.add(new Action("Consume", true));

        grid = new Grid<>();
        grid.addColumn(Action::getName).setHeader("Action");
        grid.addColumn(Action::getGranted).setHeader("Granted");

        dataProvider = DataProvider.ofCollection(actions);
        grid.setDataProvider(dataProvider);

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            // Save changes to policy
            // TO DO: implement policy update logic
        });

        add(grid, saveButton);
    }
}
