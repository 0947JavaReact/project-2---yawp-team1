import * as React from 'react';
import {useHistory} from 'react-router-dom';

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

    function submitEmail() {
        //make axios request
        setStep(2)
    }

    function tempPasswordChange (e:any) {

        setTempPass(e.target.value)
    }

    function submitTempPassword () {

        //make axios request
        setStep(3)
    }

    function passwordChange(e:any) {
        setPassword(e.target.value)
    }

    function confirmPasswordChange(e:any)
    {
        setConfirmPassword(e.target.value)
    }

    function submitPasswords() {

        if(password === confirmPassword)
        {
            //axios request
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
                    <h1>Please enter temporary password</h1>
                    <input type="text" onChange={tempPasswordChange}></input>
                    <button onClick={submitTempPassword}>Submit Temporary Password</button>
                </div>
            )

        //reset password
        case 3:
            return (
            
                <div>
                    <h1>Please reset password</h1>
                    <input type="password" placeholder="Enter Password" onChange={passwordChange}></input>
                    <input type="password" placeholder="Confirm Password" onChange={confirmPasswordChange}></input>
                    <button onClick={submitPasswords}>Submit Password</button>
                </div>

            )    
    }
    return(
        <div>
            

        </div>

    )
}

export default ResetPage

