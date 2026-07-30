import { RequireAuth } from './require-auth';

describe('RequireAuth', () => {
  it('should create an instance', () => {
    const directive = new RequireAuth();
    expect(directive).toBeTruthy();
  });
});
