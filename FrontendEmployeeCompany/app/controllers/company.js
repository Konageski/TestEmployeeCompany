import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';

export default class CompanyController extends Controller {
    @service store
    @service notify;

    @tracked company;

    initControler(id) {
        if (id !== 'new')
            this.store.findRecord('company', id).then((companyLocal) => this.company = companyLocal);
        else
            this.company = {};
    }

    @action
    async onSave() {
        if (this.company.id !== undefined && this.company.id !== null)
            await this.store.findRecord('company', this.company.id).then((companyLocal) => {
                companyLocal.id = this.company.id;
                companyLocal.name = this.company.name;
                companyLocal.save()
                    .then((ret) => {
                        this.notify.success('Company Saved!', { classNames: ['alert-success'] });
                        this.transitionToRoute('/company');
                    })
                    .catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
            });
        else
            await this.store.createRecord('company', this.company).save()
                .then((ret) => {
                    this.notify.success('Company Saved!', { classNames: ['alert-success'] });
                    this.transitionToRoute('/company');
                })
                .catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
    }

    @action
    async onDelete() {
        await this.store.findRecord('company', this.company.id).then((companyLocal) => {
            companyLocal.destroyRecord()
                .then((ret) => {
                    this.notify.success('Company Deleted!', { classNames: ['alert-success'] });
                    this.transitionToRoute('/company');
                })
                .catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
        }).catch((error) => this.notify.error(error.message, { classNames: ['alert-danger'] }));
    }

    @action
    onBack() {
        this.transitionToRoute('/company');
    }
}
