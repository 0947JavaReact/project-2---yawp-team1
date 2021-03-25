import * as React from 'react';
import {RegisterForm} from '../components/RegisterForm/RegisterForm';
import {Link} from 'react-router-dom';

function RegisterPage() {
    return (
        <div className="container">
            <h1>Welcome noob, plz register</h1>
            <RegisterForm />
            <Link to="/">Already Have an Account?</Link>
        </div>
    )
}

export default RegisterPage
