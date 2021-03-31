import * as React from 'react';
import {useHistory} from 'react-router-dom';
import axios from 'axios'
import { useDispatch, useSelector } from 'react-redux';

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
        
        try{
        let res = await axios.post("http://localhost:9025/users/sendreset", {

            email: email
        })
        
        Array.from(document.querySelectorAll("input")).forEach(
            input => (input.value = ""));


        setStep(2)
        }
        catch(e)
        {
            setStep(3)
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
                    let res = await axios.post("http://localhost:9025/users/resetpass", {

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
            
            <div>
                <h1>Please input email</h1>
                <input type="text" onChange={emailChange}></input>
                <button onClick={submitEmail}>Submit Email</button>
            </div>
            
            )

        //input new email
        case 2:
            
            return (
            
                <div>
                    <h1>Please enter emailed code</h1>
                    <input name = "temp" type="text" placeholder="Email Code"onChange={tempPasswordChange}></input>
                    <h1>Please reset password</h1>
                    <input type="password" placeholder="Enter Password" onChange={passwordChange}></input>
                    <input type="password" placeholder="Confirm Password" onChange={confirmPasswordChange}></input>
                    <button onClick={submitPasswords}>Submit Password</button>
                </div>
            )

        case 3:
            
        return(

                <div>
                    <h1>Sorry, that code was incorrect</h1>
                    <h3>Please contact our support team at yawpteam@gmail.com</h3>

                </div>
        )

    }
    return(
        <div>
            

        </div>

    )
}

export default ResetPage

