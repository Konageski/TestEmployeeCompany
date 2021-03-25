import Controller from '@ember/controller';
import { action } from '@ember/object';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';

export default class CompanyListController extends Controller {
    @service store

    @tracked companies;
    
    initControler() {
        this.store.unloadAll('company');
        this.store.findAll('company').then((companies) => { this.companies = companies });
    }

    @action
    onEdit(company) {
        this.transitionToRoute('/company/' + company.id);
    }

    @action
    onOpenNew() {
        this.transitionToRoute('/company/new');
    }
}
