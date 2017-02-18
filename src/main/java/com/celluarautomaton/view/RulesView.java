package com.celluarautomaton.view;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import com.celluarautomaton.gridmodule.GridModule;
import com.celluarautomaton.gridmodule.listener.RunningChangedListener;
import com.celluarautomaton.rulemodule.Rule;
import com.celluarautomaton.rulemodule.RuleModule;
import com.celluarautomaton.rulemodule.SchemaRule;
import com.celluarautomaton.rulemodule.SimpleRule;
import com.celluarautomaton.rulemodule.listener.RulesChangedListener;
import com.celluarautomaton.utils.WindowUtil;
import com.celluarautomaton.view.rules.RuleCell;
import com.celluarautomaton.view.rules.RuleCellCallback;

public class RulesView extends StackPane implements EventHandler<ActionEvent> {
	//private static final Logger logger = Logger.getLogger(RulesView.class);
	
	private final RuleModule ruleModule;
	private final GridModule gridModule;
	private final Button addSimpleRuleButton;
	private final Button addSchemaRuleButton;
	private final ListView<Rule> listView;
	
	public RulesView(RuleModule ruleModule, GridModule gridModule) {
		this.ruleModule = ruleModule;
		this.gridModule = gridModule;
		
		addSimpleRuleButton = new Button("Add Simple Rule");
		addSimpleRuleButton.setOnAction(this);
		addSchemaRuleButton = new Button("Add Schema Rule");
		addSchemaRuleButton.setOnAction(this);
		
		List<Rule> rules = ruleModule.getRules();
		ObservableList<Rule> observableList = FXCollections.observableArrayList(rules);
		
		final RuleCellCallback ruleCellCallback = new RuleCellCallback() {
			
			@Override
			public void onRemove(Rule rule) {
				RulesView.this.ruleModule.removeRule(rule);
			}
			
			@Override
			public void onEdit(Rule rule) {
				if(rule instanceof SimpleRule) {
					editSimpleRule((SimpleRule)rule);
				} else {
					editSchemaRule((SchemaRule)rule);
				}
			}

			@Override
			public void onMove(int from, int to) {
				RulesView.this.ruleModule.moveRule(from, to);
			}
		};
		
		listView = new ListView<>(observableList);
		//listView.setMouseTransparent(true);
		listView.setFocusTraversable(false);
		listView.setCellFactory(new Callback<ListView<Rule>, ListCell<Rule>>() {

			@Override
			public ListCell<Rule> call(ListView<Rule> arg0) {
				return new RuleCell(ruleCellCallback);
			}
		});
		
		
		VBox container = new VBox();
		container.setAlignment(Pos.TOP_CENTER);
		container.getStyleClass().add("container");
		container.getChildren().addAll(listView, addSimpleRuleButton, addSchemaRuleButton);
		
		
		BorderedTitledPane pane = new BorderedTitledPane("Rules", container);
		getChildren().add(pane);
		
		gridModule.addRunningChangedListener(new RunningChangedListener() {
			
			@Override
			public void onRunningChanged() {
				onUpdate();
			}
		});
		
		ruleModule.addRulesChangedListener(new RulesChangedListener() {
			
			@Override
			public void onRulesChanged() {
				updateRules();
			}
		});
		
		updateUI();
		updateRules();
	}
	
	private void updateRules() {
		List<Rule> rules = ruleModule.getRules();
		ObservableList<Rule> observableList = FXCollections.observableArrayList(rules);
		
		listView.setItems(observableList);
	}
	
	private void onUpdate() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				updateUI();
			}
		});
	}
	
	private void updateUI() {
		setDisable(gridModule.isRunning());
	}

	@Override
	public void handle(ActionEvent e) {
		Object source = e.getSource();
		if(source == addSimpleRuleButton) {
			addSimpleRule();
		} else if(source == addSchemaRuleButton) {
			addSchemaRule();
		}
	}
	
	private void addSimpleRule() {
		AddSimpleRuleWindow window = new AddSimpleRuleWindow();
		WindowUtil.showModal(window, "Add Simple Rule", getScene());
	}
	
	private void addSchemaRule() {
		AddSchemaRuleWindow window = new AddSchemaRuleWindow();
		WindowUtil.showModal(window, "Add Schema Rule", getScene());
	}
	
	
	private void editSimpleRule(SimpleRule simpleRule) {
		AddSimpleRuleWindow window = new AddSimpleRuleWindow(simpleRule);
		WindowUtil.showModal(window, "Edit Simple Rule", getScene());
	}
	
	private void editSchemaRule(SchemaRule schemaRule) {
		AddSchemaRuleWindow window = new AddSchemaRuleWindow(schemaRule);
		WindowUtil.showModal(window, "Edit Schema Rule", getScene());
	}
}
