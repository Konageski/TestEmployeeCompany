import Route from '@ember/routing/route';

export default class CompanyRoute extends Route {

    model(params) {
        return params.company_id;
    }

    setupController(controller, model) {
        this._super(...arguments);
        controller.initControler(model);
    }       
}
