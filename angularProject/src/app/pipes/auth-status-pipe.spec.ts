import { AuthStatusPipe } from './auth-status-pipe';

describe('AuthStatusPipe', () => {
  it('create an instance', () => {
    const pipe = new AuthStatusPipe();
    expect(pipe).toBeTruthy();
  });
});
