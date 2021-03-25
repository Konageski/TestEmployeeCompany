import { module, test } from 'qunit';
import { setupTest } from 'ember-qunit';

module('Unit | Controller | report/average-salary-by-company', function(hooks) {
  setupTest(hooks);

  // TODO: Replace this with your real tests.
  test('it exists', function(assert) {
    let controller = this.owner.lookup('controller:report/average-salary-by-company');
    assert.ok(controller);
  });
});
