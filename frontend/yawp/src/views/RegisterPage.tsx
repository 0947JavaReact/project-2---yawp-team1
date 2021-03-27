import * as React from 'react';
import {RegisterForm} from '../components/RegisterForm/RegisterForm';
import './RegisterPage.css';

function RegisterPage() {
    return (
        <div className="register-container">
            <div className="text-container">
                <h1 className="register-h1">Welcome to YAWP!</h1>
                <h2>Create an account below to start YAWPING!</h2>
            </div>
            <RegisterForm />
        </div>
    )
}

export default RegisterPage
