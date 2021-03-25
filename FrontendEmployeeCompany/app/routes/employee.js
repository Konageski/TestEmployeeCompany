import Route from '@ember/routing/route';

export default class EmployeeRoute extends Route {
    
    model(params) {
        return params.employee_id;
    }

    setupController(controller, model) {
        this._super(...arguments);
        controller.initControler(model);
    }    
}