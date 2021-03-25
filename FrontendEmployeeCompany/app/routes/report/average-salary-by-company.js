import Route from '@ember/routing/route';

export default class ReportAverageSalaryByCompanyRoute extends Route {

    setupController(controller, model) {
        this._super(...arguments);
        controller.initControler();
    }  
}
