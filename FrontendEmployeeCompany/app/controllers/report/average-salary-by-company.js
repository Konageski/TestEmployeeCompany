import Controller from '@ember/controller';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';

export default class ReportAverageSalaryByCompanyController extends Controller {
    @service store

    @tracked averageSalaryByCompanies;
    
    initControler() {
        this.store.unloadAll('reportAverageSalaryByCompany');
        this.store.findAll('reportAverageSalaryByCompany').then((averageSalaryByCompaniesLocal) => this.averageSalaryByCompanies = averageSalaryByCompaniesLocal);
    }
}