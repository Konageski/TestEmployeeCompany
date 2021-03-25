import Model, { attr } from '@ember-data/model';

export default class EmployeeModel extends Model {
    @attr name;
    @attr surname;
    @attr address;
    @attr email;
    @attr salary;
    @attr company;
}
