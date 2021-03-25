import Route from '@ember/routing/route';

export default class EmployeeListRoute extends Route {

    setupController(controller, model) {
        this._super(...arguments);
        controller.initControler();
    }  
}
