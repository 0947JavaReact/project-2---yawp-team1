import * as React from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {loginUser} from '../../actions/userActions';
import {useHistory, Link} from 'react-router-dom';
import './LoginForm.css';

export const LoginForm: React.FC<any> = () => {

    const state = useSelector<any, any>((state) => state);

    let [username, setUsername] = React.useState("");
    let [password, setPassword] = React.useState("");
    let [loggedIn, setLoggedIn] = React.useState(state.user.user.loggedIn);

    const handleChange = (e:any) => {
        if(e.target.name === "username"){
            setUsername(e.target.value);
        }
        else{
            setPassword(e.target.value);
        }
    }

    React.useEffect(()=>{
        console.log(JSON.stringify(state));
        if(state.user.user.id > 0){
            history.push('/home');
        }
        if(state.user.user.loginAttempt === 'invalid'){
            alert("username or password incorrect");
        }
    },[state.user.user.loginAttempt]);


    const dispatch =  useDispatch();

    const history = useHistory()

    const login = async ()=> {
        await dispatch(
        loginUser({username,password})
        )
    }

    return(
        <div className="login">
            <form className="login-form">
                <div className="input-div">
                    <h4 className="login-h4">Enter Username</h4>
                <input className="login-input" type="text" name="username" placeholder="Username" onChange={handleChange}/>
                </div>
                <div className="input-div">
                    <h4 className="login-h4">Enter Password</h4>
                    <input className="login-input" type="password" name="password" placeholder="Password" onChange={handleChange}/>
                </div>
            </form>
            <button className="login-button" onClick={login}>Login</button>
            <div className="login-links">
                <h4 className="login-h4">Dont have an account? <Link className="login-link" to="/register">Click here!</Link></h4>
                <h4 className="login-h4">Forgot your password? <Link className="login-link" to="/forgotpass">Reset it here!</Link></h4>
            </div>
        </div>
    )
}