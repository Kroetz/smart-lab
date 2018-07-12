package de.qaware.smartlabmonolith.service.connector.gui;

import de.qaware.smartlabapi.service.connector.gui.IGuiService;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabgui.service.controller.GuiController;
import de.qaware.smartlabcore.url.AbstractMonolithicBaseUrlGetter;
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
