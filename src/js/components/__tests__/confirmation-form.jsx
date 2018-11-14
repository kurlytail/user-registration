import React from 'react';
import ReCAPTCHA from 'react-google-recaptcha';
import Renderer from 'react-test-renderer';
import ConfirmationForm from '../confirmation-form';

jest.mock('react-google-recaptcha');

const FIXTURES = {
    actions: {
        deleteRegistration: jest.fn()
    },

    nullState: null,

    nameEvent: {
        target: {
            value: 'test-name'
        }
    },

    passwordEvent: {
        target: {
            value: 'test-name'
        }
    },

    confirmPasswordEvent: {
        target: {
            value: 'test-name'
        }
    },

    submitEvent: {
        preventDefault: jest.fn()
    }
};

describe('# confirmation-form', () => {
    beforeEach(() => {
        Object.entries(FIXTURES.actions).forEach(([name, action]) => action.mockReset());
    });

    describe('## constructor', () => {
        it('### should create default object', () => {
            const component = Renderer.create(
                <ConfirmationForm
                    email="test-email"
                    hash="test-hash"
                    state={FIXTURES.nullState}
                    actions={FIXTURES.actions}
                />
            );
            expect(component.toJSON()).toMatchSnapshot();
        });
    });

    describe('## render', () => {
        it('### render with different states', () => {
            let component = Renderer.create(
                <ConfirmationForm
                    state={FIXTURES.nullState}
                    actions={FIXTURES.actions}
                    email="test-email"
                    hash="test-hash"
                />
            );
            expect(component.toJSON()).toMatchSnapshot();
        });

        it('### render with form methods', () => {
            let component = Renderer.create(
                <ConfirmationForm
                    state={FIXTURES.nullState}
                    actions={FIXTURES.actions}
                    email="test-email"
                    hash="test-hash"
                />
            );

            let instance = component.getInstance();

            instance.handleName(FIXTURES.passwordEvent);
            expect(component.toJSON()).toMatchSnapshot();

            instance.handleConfirmPassword(FIXTURES.confirmPasswordEvent);
            expect(component.toJSON()).toMatchSnapshot();
        });

        it('### render submit methods', () => {
            const component = Renderer.create(
                <ConfirmationForm
                    state={FIXTURES.nullState}
                    actions={FIXTURES.actions}
                    email="test-email"
                    hash="test-hash"
                />
            );
            const instance = component.getInstance();
            instance.onSubmit(FIXTURES.submitEvent);

            expect(component.toJSON()).toMatchSnapshot();
            expect(FIXTURES.actions.deleteRegistration).toBeCalled();
            expect(FIXTURES.submitEvent.preventDefault).toBeCalled();
        });
    });
});
