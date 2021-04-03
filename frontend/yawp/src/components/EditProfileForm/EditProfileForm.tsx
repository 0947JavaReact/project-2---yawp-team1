import * as React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import AWS from 'aws-sdk';
import { setUser } from '../../actions/userActions';
import { useHistory, Link } from 'react-router-dom';
import './EditProfile.css';
import CameraAltIcon from '@material-ui/icons/CameraAlt';

export const EditProfileForm: React.FC<any> = (props) => {
    let [img, setImg] = React.useState<any>();
    let [bio, setBio] = React.useState<string>("");
    const history = useHistory();
    const state = useSelector<any, any>((state) => state);
    const dispatch = useDispatch();

    const getUser = async (user: any) => {
        dispatch(
            setUser(user)
        )
    };

    AWS.config.update({
        accessKeyId: "AKIAUQCHGIORLTJBOA7I",
        secretAccessKey: "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",
        region: "us-west-1",

    });

    const s3 = new AWS.S3();

    const changeBio = (e: any) => {
        setBio(e.target.value);
    };

    const changeImage = (e: any) => {
        // setImg(URL.createObjectURL(e.target.files[0]))
        setImg(e.target.files[0]);
    }

    const update = async (e: any) => {
        await updateBio(bio);
        await updatePicture(img, bio);
        history.push(`/user/${state.user.user.username}`);
    };

    const updateBio = async (bio:string) => {
        if (bio) {
            console.log(bio);
            let user = {
                username: state.user.user.username,
                id: state.user.user.id,
                bio: bio,
                email: state.user.user.email,
                profilePic: state.user.user.profilePic
            };
            console.log("inside the update bio if");
            await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/update`, {
                user_id: state.user.user.id,
                bio : bio,
                email: state.user.user.email,
                pic_url: state.user.user.profilePic
            });

            await getUser(user);
            console.log("in the update bio: " + state.user.user);
        }
    }

    const updatePicture = async (pic:any, bio:any) => {
        if (img !== undefined) {
            console.log("inside of the update picure: " + JSON.stringify(state.user.user));
            try {
                const extension = img.type.split("/")[1]; // images/extension
                const key = `${state.user.user.username}-profile-picture.${extension}`
                const params = {
                    Bucket: "robertsrevbucket",
                    Key: key,
                    ContentType: img.type,
                    Body: img,
                    ACL: 'public-read'
                };

                const pic = await s3.putObject(params).promise();
                let setBio = bio ? bio : state.user.user.bio;
                await axios.post(`http://ec2-3-101-86-38.us-west-1.compute.amazonaws.com:9025/users/update`, {
                        user_id: state.user.user.id,
                        bio: setBio,
                        email: state.user.user.email,
                        pic_url: `https://robertsrevbucket.s3-us-west-1.amazonaws.com/${key}`
                });

                console.log(`https://robertsrevbucket.s3-us-west-1.amazonaws.com/${key}`);

                let user = {
                        username: state.user.user.username,
                        id: state.user.user.id,
                        bio: setBio,
                        email: state.user.user.email,
                        profilePic: `https://robertsrevbucket.s3-us-west-1.amazonaws.com/${key}`
                    };

                await getUser(user);
            } catch (error) {
                console.log(error);
            
            }
        }
    }

    return (
        <div className="edit-container">
            <div className="img-div">
                <label htmlFor="file-upload" className="pic-input-default">
                    {img ? (<div><img className="pic-input-default" src={img ? URL.createObjectURL(img) : ''} /></div>) : <CameraAltIcon />}
                </label>
                <input id="file-upload" className="pic-input" type="file" accept="image/*" onChange={changeImage} />
            </div>
        <div className="edit-bio">
            <h5 className='edit-h5'>Edit Bio</h5>
            <textarea className="edit-bio-text"cols={50} maxLength={255} placeholder="Edit your bio" onChange={changeBio} />
        </div>
            <br />
            <button className="edit-submit" onClick={update}>Update</button>
            <Link className="edit-link" to={`/user/${props.username}`}>Return to profile page</Link>
        </div>
    );
}