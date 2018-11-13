import { createResource } from 'redux-rest-resource';

class RegistrationReducer {
    constructor() {
        this.STATE_PATH = 'app.registration';

        const { actions, rootReducer } = createResource({
            name: 'registration',
            pluralName: 'registration',
            url: '/api/registration',
            actions: {
                confirm: { method: 'POST', url: './confirm' }
            }
        });

        this.reducer = rootReducer;
        Object.assign(this, actions);
    }
}

export default RegistrationReducer;
