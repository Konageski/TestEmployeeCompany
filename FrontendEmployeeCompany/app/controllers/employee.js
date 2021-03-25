import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';

export default class EmployeeController extends Controller {
    @service store
    @service notify;

    @tracked companies;
    @tracked employee;
    @tracked selectedCompany;

    initControler(id) {
        if (id !== 'new')
            this.store.findRecord('employee', id).then((employeeLocal) => {
                this.employee = employeeLocal;

                this.store.findAll('company').then((companies) => {
                    this.companies = companies;
                    this.companies.forEach(company => {
                        if (company.id == this.employee.company.id)
                            this.selectedCompany = company.id;
                    });
                });
            });
        else {
            this.employee = {};
            this.store.findAll('company').then((companies) => {
                this.companies = companies;
            });
        }
    }

    @action
    async onSave() {
        if (this.employee.id !== undefined && this.employee.id !== null)
            await this.store.findRecord('employee', this.employee.id).then((employeeLocal) => {
                employeeLocal.id = this.employee.id;
                employeeLocal.name = this.employee.name;
                employeeLocal.surname = this.employee.surname;
                employeeLocal.email = this.employee.email;
                employeeLocal.address = this.employee.address;
                employeeLocal.salary = this.employee.salary;
                employeeLocal.company.id = this.employee.company.id;
                employeeLocal.save()
                    .then((ret) => {
                        this.notify.success('Employee Saved!', { classNames: ['alert-success'] });
                        this.transitionToRoute('/employee');
                    })
                    .catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
            });
        else
            await this.store.createRecord('employee', this.employee).save()
                .then((ret) => {
                    this.notify.success('Employee Saved!', { classNames: ['alert-success'] });
                    this.transitionToRoute('/employee');
                })
                .catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
    }

    @action
    async onDelete() {
        await this.store.findRecord('employee', this.employee.id).then((employeeLocal) => {
            employeeLocal.destroyRecord()
                .then((ret) => {
                    this.notify.success('Employee Deleted!', { classNames: ['alert-success'] });
                    this.transitionToRoute('/employee');
                })
                .catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
        }).catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
    }

    @action
    onBack() {
        this.transitionToRoute('/employee');
    }

    @action
    onChangeCompany(id) {
        if (this.employee.company == undefined)
            this.employee.company = {};
        this.employee.company.id = id;
    }
}
