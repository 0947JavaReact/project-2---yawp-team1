import React, {useRef} from 'react';
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
                message: cyTextarea.current.value,
                authorId: state.user.user.id,
                authorUsername: state.user.user.username,
                authorPic: state.user.user.profilePic,
                likes: []
            };

            dispatch(
                postYawp(yawp)
            )

            cyTextarea.current.value = '';
        }
    };

    console.log(state);

    return (
        <div>
        <div className="create-container">
            <div className="content-container">
                <img className="profile-pic" src={state.user.user.profilePic} alt="profile-pic"/>
                <textarea className="create-yawp-textarea" ref={cyTextarea} onChange={autogrow} cols={50} maxLength={250} placeholder="Create your YAWP"></textarea>
            </div>
            <button className="create-yawp-button" onClick={sendYawp}>Send Yawp</button>
        </div>
        </div>
    )
}

export default CreateYawp;
