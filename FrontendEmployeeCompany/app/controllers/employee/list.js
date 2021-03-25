import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';

export default class EmployeeListController extends Controller {
    @service store

    @tracked employees;
 
    initControler() {
        this.store.unloadAll('employee');
        this.store.findAll('employee').then((employees) => this.employees = employees);
    }

    @action
    onEdit(employee) {
        this.transitionToRoute('/employee/' + employee.id);
    }

    @action
    onOpenNew() {
        this.transitionToRoute('/employee/new');
    }
}
