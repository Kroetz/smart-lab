package de.qaware.smartlabmonolith.service.connector.gui;

import de.qaware.smartlab.api.service.connector.gui.IGuiService;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.gui.service.controller.GuiController;
import de.qaware.smartlab.core.service.url.AbstractMonolithicBaseUrlGetter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
public class GuiMonolithicServiceConnector implements IGuiService {

    @Component
    // TODO: String literal
    @Qualifier("guiServiceBaseUrlGetter")
    @ConditionalOnProperty(
            prefix = Property.Prefix.MODULARITY,
            name = Property.Name.MODULARITY,
            havingValue = Property.Value.Modularity.MONOLITH)
    public static class BaseUrlGetter extends AbstractMonolithicBaseUrlGetter {

        public BaseUrlGetter(GuiController.BaseUrlController baseUrlController) {
            super(baseUrlController);
        }
    }
}
