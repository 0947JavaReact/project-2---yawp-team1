import * as React from 'react';
import {Link} from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import {EditProfileForm} from '../components/EditProfileForm/EditProfileForm';
import './EditProfilePage.css';
function EditProfilePage(props:any) {

    const state = useSelector<any, any>((state) => state);

    return(
        
        <div className="edit-page">
            <EditProfileForm username={state.user.user.username}></EditProfileForm>
        </div>

    )
}

export default EditProfilePage;