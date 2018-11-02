import React from 'react';
import ReCAPTCHA from 'react-google-recaptcha';
import Renderer from 'react-test-renderer';
import RegistrationForm from '../registration-form';

jest.mock('react-google-recaptcha');

const FIXTURES = {
    actions: {
        getRegistration: jest.fn(),
        createRegistration: jest.fn()
    },

    nullState: null,

    fetchingState0: {},

    fetchingState1: {
        isFetching: true
    },

    fetchingState2: {
        item: {}
    },

    fetchingState3: {
        item: {},
        isFetching: true
    },

    fetchedState: {
        item: { captchaKey: 'test-key' },
        isFetching: false
    },

    emailEvent: {
        target: {
            value: 'test-email'
        }
    },

    submitEvent: {
        preventDefault: jest.fn()
    }
};

describe('# registration-form', () => {
    beforeEach(() => {
        Object.entries(FIXTURES.actions).forEach(([name, action]) => action.mockReset());
    });

    describe('## constructor', () => {
        it('### should create default object', () => {
            const component = Renderer.create(<RegistrationForm />);
            expect(component.toJSON()).toMatchSnapshot();
        });
    });

    describe('## render', () => {
        it('### render with different states', () => {
            let component = Renderer.create(<RegistrationForm state={FIXTURES.nullState} actions={FIXTURES.actions} />);
            expect(component.toJSON()).toMatchSnapshot();

            component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchingState0} actions={FIXTURES.actions} />
            );
            expect(component.toJSON()).toMatchSnapshot();

            component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchingState1} actions={FIXTURES.actions} />
            );
            expect(component.toJSON()).toMatchSnapshot();

            component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchingState2} actions={FIXTURES.actions} />
            );
            expect(component.toJSON()).toMatchSnapshot();

            component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchingState3} actions={FIXTURES.actions} />
            );
            expect(component.toJSON()).toMatchSnapshot();
        });

        it('### render with stable states', () => {
            const component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchedState} actions={FIXTURES.actions} />
            );
            expect(component.toJSON()).toMatchSnapshot();
        });

        it('### render with form methods', () => {
            let component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchedState} actions={FIXTURES.actions} />
            );

            let instance = component.getInstance();

            instance.handleEmail(FIXTURES.emailEvent);
            expect(component.toJSON()).toMatchSnapshot();

            instance.dataCallback('test captcha hash');
            expect(component.toJSON()).toMatchSnapshot();

            component = Renderer.create(<RegistrationForm state={FIXTURES.fetchedState} actions={FIXTURES.actions} />);
            instance = component.getInstance();

            instance.dataCallback('test captcha hash');
            expect(component.toJSON()).toMatchSnapshot();

            instance.handleEmail(FIXTURES.emailEvent);
            expect(component.toJSON()).toMatchSnapshot();

            component = Renderer.create(<RegistrationForm state={FIXTURES.fetchedState} actions={FIXTURES.actions} />);
            instance = component.getInstance();

            instance.dataExpiredCallback();
            expect(component.toJSON()).toMatchSnapshot();

            instance.handleEmail(FIXTURES.emailEvent);
            expect(component.toJSON()).toMatchSnapshot();
        });

        it('### render submit methods', () => {
            const component = Renderer.create(
                <RegistrationForm state={FIXTURES.fetchedState} actions={FIXTURES.actions} />
            );
            const instance = component.getInstance();
            instance.onSubmit(FIXTURES.submitEvent);
            expect(component.toJSON()).toMatchSnapshot();
            expect(FIXTURES.actions.createRegistration).toBeCalled();
            expect(FIXTURES.submitEvent.preventDefault).toBeCalled();
        });
    });
});
