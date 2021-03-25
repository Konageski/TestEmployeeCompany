import Model, { attr } from '@ember-data/model';

export default class ReportAverageSalaryByCompanyModel extends Model {              
    @attr name;
    @attr averageSalary;
}
