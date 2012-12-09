/*
 * Copyright 2012 Sven Meier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicketbox.examples;

import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

import wicketbox.component.WizardBox;
import wicketbox.component.theme.BasicTheme;

/**
 * @author Sven Meier
 */
public class WizardBoxExamplePage extends ExamplePage {

	public WizardBoxExamplePage() {
		WizardBox wizard = new WizardBox("wizard", newWizardModel());
		wizard.add(new BasicTheme());
		add(wizard);
	}

	private IWizardModel newWizardModel() {
		WizardModel wizard = new WizardModel();

		wizard.add(new ExampleWizardStep("one"));
		wizard.add(new ExampleWizardStep("two"));
		wizard.add(new ExampleWizardStep("three"));
		wizard.add(new ExampleWizardStep("four"));

		return wizard;
	}

	private class ExampleWizardStep extends WizardStep {

		public ExampleWizardStep(String key) {
			super(new ResourceModel(key + ".title"), new ResourceModel(key
					+ ".summary"));

			add(new Label("label", new ResourceModel(key + ".label")));
		}
	}
}