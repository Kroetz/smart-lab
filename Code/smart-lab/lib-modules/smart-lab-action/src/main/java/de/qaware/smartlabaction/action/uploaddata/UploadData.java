package de.qaware.smartlabaction.action.uploaddata;

import de.qaware.smartlabcommons.api.internal.service.delegate.IDelegateService;
import de.qaware.smartlabaction.action.AbstractAction;
import de.qaware.smartlabaction.action.IActionArgs;
import de.qaware.smartlabaction.action.IActionDispatching;
import de.qaware.smartlabaction.action.IActionExecution;
import de.qaware.smartlabaction.action.result.IActionResult;
import de.qaware.smartlabaction.action.result.VoidActionResult;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcommons.exception.UnknownServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UploadData extends AbstractAction {

    public static final String ACTION_ID = "upload data";
    private IResolver<String, IUploadDataService> uploadDataServiceResolver;

    public UploadData(IResolver<String, IUploadDataService> uploadDataServiceResolver) {
        super(ACTION_ID);
        this.uploadDataServiceResolver = uploadDataServiceResolver;
    }

    public IActionExecution<Void> execution(ActionArgs actionArgs) {
        return (actionService) -> {
            IActionResult actionResult = actionService.executeAction(UploadData.ACTION_ID, actionArgs);
            return actionResult.getVoidValue();
        };
    }

    @Override
    public IActionDispatching dispatching(String deviceType, IActionArgs genericActionArgs) {
        // TODO: Is never needed because there is no local execution for a web service.
        return () -> VoidActionResult.instance();
    }

    @Override
    public IActionDispatching dispatching(IActionArgs genericActionArgs, IDelegateService delegateService) {
        // Every action can only handle its own specific argument type.
        // TODO: Move this call somewhere else so that this method always gets the right action args type (parameterized?)
        ActionArgs actionArgs = convertToSpecific(UploadData.ActionArgs.class, genericActionArgs);
        return () -> {
            IUploadDataService uploadDataService = this.uploadDataServiceResolver
                    .resolve(actionArgs.getKnowledgeBaseInfo().getServiceId())
                    .orElseThrow(UnknownServiceException::new);
            uploadDataService.upload(actionArgs.getKnowledgeBaseInfo(), actionArgs.getDataToUpload());
            return VoidActionResult.instance();
        };
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")     // TODO: Eliminate string literal
    @NoArgsConstructor // TODO: Really necessary for objects being able to serialize/deserialize?
    public static class ActionArgs implements IActionArgs {

        @NonNull
        private IKnowledgeBaseInfo knowledgeBaseInfo;

        @NonNull
        private String dataToUpload;
    }
}
