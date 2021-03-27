import * as React from 'react';
import {LoginForm} from '../components/LoginForm/LoginForm';
import {Link} from 'react-router-dom';
import './LoginPage.css';


function LoginPage() {
    return (
        <div className="login-container">
            <div className="text-container">
                <h1 className="login-h1">Welcome to YAWP!</h1>
                <h2>Sign it to see what everyone's YAWPING about!</h2>
            </div>
            <LoginForm />
        </div>
    )
}

export default LoginPage
