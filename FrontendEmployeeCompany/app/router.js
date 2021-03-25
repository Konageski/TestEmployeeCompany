import EmberRouter from '@ember/routing/router';
import config from 'frontend-employee-company/config/environment';

export default class Router extends EmberRouter {
	location = config.locationType;
	rootURL = config.rootURL;
}

Router.map(function () {
	this.route('company/list', { path: '/company' });
	this.route('company', { path: '/company/:company_id' });
	this.route('employee/list', { path: '/employee' });
	this.route('employee', { path: '/employee/:employee_id' });

	this.route('report', function () {
		this.route('average-salary-by-company');
	});

	this.route('not-found', { path: '/*path' });
});
