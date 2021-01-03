package dev.c20.workflow.commons.wrapper;

import dev.c20.workflow.commons.wrapper.entities.Storage;
import dev.c20.workflow.commons.wrapper.entities.adds.*;
import dev.c20.workflow.commons.wrapper.responses.ListResponse;
import dev.c20.workflow.commons.wrapper.responses.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class RemoteStorageService {

    @Autowired
    StorageRestCall storageRestCall;

    String targetContext; // ejem: workflow
    String serviceContext; // ejem: storage or task
    String auth;

    public void init(String server, String targetContext, String auth) {
        storageRestCall.setServer(server);
        this.targetContext = targetContext;
        this.auth = auth;
    }

    public void callStorageService(HttpMethod httpMethod, String path, Object body) {
        storageRestCall
                .setHttpMethod(httpMethod)
                .setWebContext(this.targetContext + "/storage" + path)
                .setHeader("Authorization",auth)
                .setBody(body)
                .send();
    }

    public ObjectResponse<Storage> createTree(String path, Storage storage ) {
        callStorageService(HttpMethod.POST,"/tree" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> createFolder(String path, Storage storage ) {
        callStorageService(HttpMethod.POST,"/folder" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> readFolder(String path ) {
        callStorageService(HttpMethod.GET,"/folder" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> updateFolder(String path, Storage storage ) {
        callStorageService(HttpMethod.PUT,"/folder" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> deleteFolder(String path ) {
        callStorageService(HttpMethod.DELETE,"/folder" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    // files
    public ObjectResponse<Storage> createFile(String path,Storage storage ) {
        callStorageService(HttpMethod.POST,"/file" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> readFile(String path ) {
        callStorageService(HttpMethod.GET,"/file" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);

    }

    public ObjectResponse<Storage> updateFile(String path, Storage storage ) {
        callStorageService(HttpMethod.PUT,"/file" + path,storage);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Storage> deleteFile(String path ) {
        callStorageService(HttpMethod.DELETE,"/file" + path,null);
        return (new ObjectResponse<Storage>()).setResponse(storageRestCall.response);
    }

    // attachments
    public ObjectResponse<Attach> createAttach(String path, Attach attach ) {
        callStorageService(HttpMethod.POST,"/attach" + path,attach);
        return (new ObjectResponse<Attach>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Attach> updateAttach(String path, Attach attach ) {
        callStorageService(HttpMethod.PUT,"/attach" + path,attach);
        return (new ObjectResponse<Attach>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Attach> deleteAttach(String path, Attach attach ) {
        callStorageService(HttpMethod.DELETE,"/attach" + path,attach);
        return (new ObjectResponse<Attach>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Attach> getAttachments(String path ) {
        callStorageService(HttpMethod.DELETE,"/attach" + path,null);
        return (new ListResponse<Attach>()).setResponse(storageRestCall.response);
    }

    // data
    public ObjectResponse<Data> createData(String path, Data data ) {
        callStorageService(HttpMethod.POST,"/data" + path,data);
        return (new ObjectResponse<Data>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Data> getData(String path ) {
        callStorageService(HttpMethod.POST,"/data" + path,null);
        return (new ObjectResponse<Data>()).setResponse(storageRestCall.response);
    }

    // log
    public ObjectResponse<Log> createLog(String path, Log log ) {
        callStorageService(HttpMethod.POST,"/log" + path,log);
        return (new ObjectResponse<Log>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Log> getLog(String path ) {
        callStorageService(HttpMethod.POST,"/log" + path,null);
        return (new ListResponse<Data>()).setResponse(storageRestCall.response);
    }

    // notes
    public ObjectResponse<Note> createNote(String path, Note note ) {
        callStorageService(HttpMethod.POST,"/note" + path,note);
        return (new ObjectResponse<Note>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Note> getNotes(String path ) {
        callStorageService(HttpMethod.POST,"/note" + path,null);
        return (new ListResponse<Note>()).setResponse(storageRestCall.response);
    }

    // values
    public ObjectResponse<Value> createValue(String path, Value value ) {
        callStorageService(HttpMethod.POST,"/value" + path,value);
        return (new ObjectResponse<Value>()).setResponse(storageRestCall.response);
    }

    public ObjectResponse<Value> updateValue(String path, Value value ) {
        callStorageService(HttpMethod.PUT,"/value" + path,value);
        return (new ObjectResponse<Value>()).setResponse(storageRestCall.response);
    }

    public ListResponse<Value> getValues(String path ) {
        callStorageService(HttpMethod.GET,"/value" + path,null);
        return (new ListResponse<Value>()).setResponse(storageRestCall.response);
    }


}
