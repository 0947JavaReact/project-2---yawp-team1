import * as React from 'react';
import {LoginForm} from '../components/LoginForm/LoginForm';
import {Link} from 'react-router-dom';


function LoginPage() {
    return (
        <div className="container">
            <h1>Welcome to our app, login plz</h1>
            <LoginForm />
            <Link to="/forgotpass">ForgotPassword</Link>
        </div>
    )
}

export default LoginPage
