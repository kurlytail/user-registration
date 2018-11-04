import { createResource } from 'redux-rest-resource';

class RegistrationReducer {
    constructor() {
        this.STATE_PATH = 'app.registration';

        const { actions, rootReducer } = createResource({
            name: 'registration',
            pluralName: 'registration',
            url: '/api/registration'
        });

        this.reducer = rootReducer;
        Object.assign(this, actions);
    }
}

export RegistrationReducer;
