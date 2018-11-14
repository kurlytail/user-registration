import React, { Component } from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';

class RegistrationConfirmationForm extends Component {
    constructor(props) {
        super(props);
        this.state = { hash: props.hash, email: props.email };
    }

    handlePassword(event) {
        this.setState({ password: event.target.value });
    }

    handleConfirmPassword(event) {
        this.setState({ confirmPassword: event.target.value });
    }

    handleName(event) {
        this.setState({ name: event.target.value });
    }

    onSubmit(e) {
        e.preventDefault();
        this.props.actions.deleteRegistration({ ...this.state });
    }

    render() {
        let fieldErrors = _.get(this.props.state, 'error.errors');
        fieldErrors = fieldErrors ? fieldErrors : [];

        return (
            <form className="auth-form" method="POST" id="user-registration-form" encType="utf8">
                <div className="form-group">
                    <div className="form-label-group">
                        <label>email</label>
                        <input
                            type="email"
                            className="form-control"
                            id="user-registration-email"
                            readOnly
                            value={this.state.email}
                        />
                    </div>
                    <div className="form-label-group">
                        <label>Name</label>
                        <input
                            type="text"
                            className="form-control"
                            required
                            autoFocus
                            id="user-registration-name"
                            onChange={event => this.handleName(event)}
                            value={this.state.name}
                        />
                        {fieldErrors.map(
                            errorItem =>
                                errorItem.field === 'name' ? (
                                    <p className="text-red" key={errorItem.code}>
                                        {errorItem.defaultMessage}
                                    </p>
                                ) : (
                                    ''
                                )
                        )}
                    </div>
                    <div className="form-label-group">
                        <label>Password</label>
                        <input
                            type="password"
                            className="form-control"
                            required
                            id="user-registration-password"
                            onChange={event => this.handlePassword(event)}
                            value={this.state.password}
                        />
                        {fieldErrors.map(
                            errorItem =>
                                errorItem.field === 'password' ? (
                                    <p className="text-red" key={errorItem.code}>
                                        {errorItem.defaultMessage}
                                    </p>
                                ) : (
                                    ''
                                )
                        )}
                    </div>
                    <div className="form-label-group">
                        <label>Confirm Password</label>
                        <input
                            type="password"
                            className="form-control"
                            required
                            id="user-registration-confirmPassword"
                            onChange={event => this.handleConfirmPassword(event)}
                            value={this.state.confirmPassword}
                        />
                        {fieldErrors.map(
                            errorItem =>
                                errorItem.field === 'confirmPassword' ? (
                                    <p className="text-red" key={errorItem.code}>
                                        {errorItem.defaultMessage}
                                    </p>
                                ) : (
                                    ''
                                )
                        )}
                    </div>
                </div>
                <div className="form-group">
                    <button
                        className="btn btn-lg btn-primary btn-block"
                        type="submit"
                        id="signup-button"
                        onClick={e => this.onSubmit(e)}
                    >
                        Confirm Registration
                    </button>
                </div>
            </form>
        );
    }
}

RegistrationConfirmationForm.propTypes = {
    hash: PropTypes.string,
    email: PropTypes.string,
    state: PropTypes.any,
    actions: PropTypes.any
};

export default RegistrationConfirmationForm;
