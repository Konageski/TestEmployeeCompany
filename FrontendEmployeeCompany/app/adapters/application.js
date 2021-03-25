import RESTAdapter from '@ember-data/adapter/rest';

export default class ApplicationAdapter extends RESTAdapter {
    // host = "http://localhost:8080";
    host = "http://192.168.1.105:8080";
    namespace = "api/1.0";

    handleResponse = (status, headers, payload, requestData) => {
        return payload;
    }

}
