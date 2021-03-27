import * as React from 'react';
import {Link} from 'react-router-dom';
import './RegisterForm.css';

export const RegisterForm: React.FC<any> = () => {

    let [register, setRegister] = React.useState({
        username:'',
        email: '',
        password: '',
        password2: ''
    });

    const handleChange = (e:any) => {
        setRegister({...register, [e.target.name]:e.target.value})
    }

    const registerUser = (e:any) => {
        console.log(register);
        if(register.password !== register.password2){
            alert("passwords must match");
            return;
        }
        /* Axios Request */
        Array.from(document.querySelectorAll("input")).forEach(
        input => (input.value = "")
        );
    }

    return(
        <div className="register">
            <form className="register-form" id="register-id" onSubmit={registerUser}>
                <div className="input-div">
                    <h4 className="register-h4">Enter Username</h4>
                    <input autoComplete="off" className="register-input" type="text" name="username" placeholder="Username" onChange={handleChange} required/>
                </div>
                <div className="input-div">
                    <h4 className="register-h4">Enter Email</h4>
                    <input autoComplete="off" className="register-input" type="email" name="email" placeholder="Email" onChange={handleChange} required/>
                </div>
                <div className="input-div">
                    <h4 className="register-h4">Password</h4>
                    <input className="register-input" type="password" name="password" placeholder="Password" onChange={handleChange} required/>
                </div>
                <div className="input-div">
                    <h4 className="register-h4">Confirm Password</h4>
                    <input className="register-input" type="password" name="password2" placeholder="Confirm Password" onChange={handleChange} required/>
                </div>
            </form>
            <button className="register-button" onClick={registerUser} >Register</button>
            <div className="register-links">
                <h4 className="register-h4">Already have an account? <Link className="register-link" to="/">Login here!</Link></h4>
            </div>
        </div>
    )
}