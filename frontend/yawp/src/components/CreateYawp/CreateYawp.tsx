import React, { useState, useRef, createRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import './CreateYawp.css';
import img from '../../img/profile-picture-default.jpeg';
import { postYawp } from "../../actions/yawpActions";

export interface Yawp {
    id: number,
    content: string,
    username: string,
    profilePic: string
    likes: number
};

export const CreateYawp: React.FC<any> = () => {
    const cyTextarea = useRef<HTMLTextAreaElement>(null);

    const autogrow = () => {
        if (cyTextarea && cyTextarea.current) {
            console.log(cyTextarea.current.style.height);

            // if (cyTextarea.current.value.length % 50 == 0) {
            cyTextarea.current.style.height = "25px";
            cyTextarea.current.style.height = cyTextarea.current.scrollHeight + "px";
            // }
        }
    };

    const state = useSelector<any, any>((state) => state);
    const dispatch = useDispatch();

    const sendYawp = () => {
        if (cyTextarea && cyTextarea.current) {
            const yawp = {
                id: -1,
                username: state.user.user.username,
                content: cyTextarea.current.value,
                likes: 0,
                profilePic: state.user.user.profilePic
            };

            dispatch(
                postYawp(yawp)
            )
        }
    };

    return (
        <div>
        <div className="create-container">
            <div className="content-container">
                <img className="profile-pic" src={img} />
                <textarea className="create-yawp-textarea" ref={cyTextarea} onChange={autogrow} cols={50} maxLength={250} placeholder="Create your YAWP"></textarea>
            </div>
            <button className="create-yawp-button" onClick={sendYawp}>Send Yawp</button>
        </div>
        </div>
    )
}

export default CreateYawp;
