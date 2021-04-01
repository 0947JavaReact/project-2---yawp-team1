import * as React from 'react';
import {LoginForm} from '../components/LoginForm/LoginForm';
import {useHistory} from 'react-router-dom';
import logo from '../img/YAWP_logo.gif';
import './LoginPage.css';


function LoginPage() {

    let [showPage, setShowPage] = React.useState(false);
    const history = useHistory();

    React.useEffect(() => {
        if(localStorage.getItem('id')){
            history.push('/home');
        }
        else{
            setShowPage(true);
        }
    },[])

    return (
        <div> {showPage ? (<div className="login-container">
            <div>
                <img src={logo} className="logo"/>
            </div>
            <div className="text-container">
                <h1 className="login-h1">Welcome to YAWP!</h1>
                <h2>Sign in to see what everyone's YAWPING about!</h2>
            </div>
            <LoginForm className="login-form-component"/>
        </div>) : <></>}
        </div>
    )
}

export default LoginPage
