package dev.c20.workflow.commons.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.c20.workflow.commons.tools.StringUtils;
import dev.c20.workflow.commons.wrapper.entities.Storage;
import dev.c20.workflow.commons.wrapper.responses.ListResponse;
import dev.c20.workflow.commons.wrapper.responses.ObjectResponse;
import dev.c20.workflow.commons.wrapper.responses.TaskInstance;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RemoteTaskService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    StorageRestCall storageRestCall;

    String targetContext;
    String auth;

    public void init(String server, String targetContext, String auth) {
        storageRestCall.setServer(server);
        this.targetContext = targetContext;
        this.auth = auth;
    }

    public void callStorageService(HttpMethod httpMethod, String path, Object body) {
        logger.info("Call remote server:" + storageRestCall.getServer());
        logger.info("Call remote context:" + this.targetContext);
        logger.info("Call remote service:" + path);
        storageRestCall
                .setHttpMethod(httpMethod)
                .setWebContext(this.targetContext + path)
                .setHeader("Authorization",auth)
                .setBody(body)
                .send();
    }

    public List<Map<String,Object>> getAll() throws Exception {

        callStorageService(HttpMethod.GET,"/task",null);
        List<Map<String,Object>> result = (List<Map<String,Object>>)StringUtils.fromJSON(storageRestCall.response);
        return result;
    }

    public TaskInstance start(String path, Map<String,Object> data ) {

        callStorageService(HttpMethod.POST,"/task" + path,data);
        return (new TaskInstance()).setResponse(storageRestCall.response);
    }

    public TaskInstance update(String path, Map<String,Object> data ) {

        callStorageService(HttpMethod.POST,"/task" + path,data);
        return (new TaskInstance()).setResponse(storageRestCall.response);
    }

    public TaskInstance delete(String path, Map<String,Object> data ) {

        callStorageService(HttpMethod.POST,"/task" + path,data);
        return (new TaskInstance()).setResponse(storageRestCall.response);
    }

    public TaskInstance complete(String path, Map<String,Object> data ) {

        callStorageService(HttpMethod.POST,"/task" + path,data);
        return (new TaskInstance()).setResponse(storageRestCall.response);
    }


}
