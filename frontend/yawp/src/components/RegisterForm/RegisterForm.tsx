import * as React from 'react';
import {useDispatch, useSelector} from 'react-redux';

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
                <h4 className="register-h4">Enter Username</h4>
                <input type="text" name="username" placeholder="Username" onChange={handleChange} required/>
                <h4 className="register-h4">Enter Email</h4>
                <input type="email" name="email" placeholder="Email" onChange={handleChange} required/>
                <h4 className="register-h4">Password</h4>
                <input type="password" name="password" placeholder="Password" onChange={handleChange} required/>
                <h4 className="register-h4">Confirm Password</h4>
                <input type="password" name="password2" placeholder="Confirm Password" onChange={handleChange} required/>
            </form>
            <button onClick={registerUser} >Register</button>
        </div>
    )
}