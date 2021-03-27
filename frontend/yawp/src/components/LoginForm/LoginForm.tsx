import * as React from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {loginUser} from '../../actions/userActions';
import {useHistory, Link} from 'react-router-dom';
import axios from 'axios';

export const LoginForm: React.FC<any> = () => {

    let [username, setUsername] = React.useState("");
    let [password, setPassword] = React.useState("");

    const handleChange = (e:any) => {
        if(e.target.name === "username"){
            setUsername(e.target.value);
        }
        else{
            setPassword(e.target.value);
        }
    }

    const dispatch =  useDispatch();

    const history = useHistory()

    const login = async ()=> {
        console.log("before dispatch");
        await dispatch(
        loginUser({username,password})
    )
    console.log("After dispatch");
    history.push('/home')
  }

    return(
        <div className="login">
            <form className="login-form">
                <h4 className="login-h4">Enter Username</h4>
                <input type="text" name="username" placeholder="Username" onChange={handleChange}/>
                <h4 className="login-h4">Enter Password</h4>
                <input type="password" name="password" placeholder="Password" onChange={handleChange}/>
            </form>
            <button onClick={login}>Login</button>
            <Link to="/register">Register</Link>
        </div>
    )
}