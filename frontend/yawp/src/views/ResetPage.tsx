import * as React from 'react';
import {useHistory, Link} from 'react-router-dom';
import axios from 'axios'
import './ResetPage.css';

function ResetPage() {

    let [step, setStep] = React.useState(1)
    let [email, setEmail] = React.useState("")
    let[tempPass, setTempPass] = React.useState("")
    let[password, setPassword] = React.useState("")
    let[confirmPassword, setConfirmPassword] = React.useState("")

    const history = useHistory();

    function emailChange(e:any) {

        setEmail(e.target.value)
    }

    async function submitEmail() {
        console.log(email);
        try{
        let res = await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/sendreset", {
            email: email
        })
        
        Array.from(document.querySelectorAll("input")).forEach(
            input => (input.value = ""));


        setStep(2)
        }
        catch(e)
        {
            console.log(e);
            //setStep(3)
            return
        }
    }

    function tempPasswordChange (e:any) {

        setTempPass(e.target.value)
    }

    function passwordChange(e:any) {
        setPassword(e.target.value)
    }

    function confirmPasswordChange(e:any)
    {
        setConfirmPassword(e.target.value)
    }

    async function submitPasswords() {

        if(password === confirmPassword)
        {
            try{
                    let res = await axios.post("http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/resetpass", {

                        email: email,
                        temp_password: tempPass,
                        new_password: password
                    })
            }
            catch(e)
            {
                setStep(3)
                return
            }

            history.push("/")
        }
        else
        {
            alert("Passwords do not match")

            return
        }

        
    }
    switch(step)
    {
        //input email
        case 1:

            return (
            
            <div className="reset-container">
                <div className="step-container">
                    <h1 className="step-one-h1">Forgot your password?</h1>
                    <h4 className="step-one-h4">Please enter your accounts email below</h4>
                    <input className="reset-input" type="text" onChange={emailChange}></input>
                    <button className="reset-button" onClick={submitEmail}>Submit Email</button>
                </div>
            </div>
            
            )

        //input new email
        case 2:
            
            return (
                <div className="reset-container">
                <div className="step-container">
                    <h1 className="step-one-h1">We emailed you a temporary code.</h1>
                    <h4 className="step-one-h4">Please enter the temporary code below.</h4>
                    <input className="reset-input" name = "temp" type="text" placeholder="Email Code"onChange={tempPasswordChange}></input>
                    <h1 className="step-one-h1">Please enter your new password.</h1>
                    <h4 className="step-one-h4">Enter and confirm the new password you would like to use.</h4>
                    <input className="reset-input" type="password" placeholder="Enter Password" onChange={passwordChange}></input>
                    <input className="reset-input" type="password" placeholder="Confirm Password" onChange={confirmPasswordChange}></input>
                    <button className="reset-button" onClick={submitPasswords}>Submit Password</button>
                </div>
                </div>
            )

        case 3:
            
        return(

                <div className="reset-container">
                    <div className="step-container">
                        <h1 className="step-one-h1">Sorry, an error occured!</h1>
                        <h4 className="step-one-h4">Please contact our support team at yawpteam@gmail.com</h4>
                        <Link to="/" className="link">Redirect to the login page.</Link>
                    </div>
                </div>
        )

    }
    return(
        <div>
            

        </div>

    )
}

export default ResetPage

