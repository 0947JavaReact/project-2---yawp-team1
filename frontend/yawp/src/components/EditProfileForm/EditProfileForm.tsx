import * as React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import AWS from 'aws-sdk';
import { setUser } from '../../actions/userActions';

export const EditProfileForm: React.FC<any> = () => {
    let [img, setImg] = React.useState<any>();
    let [bio, setBio] = React.useState<string>("");
    const state = useSelector<any, any>((state) => state);
    const dispatch = useDispatch();
    const getUser = (user: any) => {
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

    const upload = (e: any) => {
        if (img !== undefined) {
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

                const res = s3.putObject(params).promise().then(() => {
                    console.log(state.user.user.id);
                    axios.post(`http://localhost:9025/users/update`, {
                        user_id: state.user.user.id,
                        bio: state.user.user.bio,
                        email: state.user.user.email,
                        pic_url: `https://robertsrevbucket.s3-us-west-1.amazonaws.com/${key}`
                    });

                    let user = {
                        username: state.user.user.username,
                        id: state.user.user.id,
                        bio: state.user.user.bio,
                        email: state.user.user.email,
                        profilePic: `https://robertsrevbucket.s3-us-west-1.amazonaws.com/${key}`
                    };

                    getUser(user);
                });
            } catch (error) {
                console.log(error);
                return;
            }
        }

        if (bio !== "") {
            axios.post(`http://localhost:9025/users/update`, {
                user_id: state.user.user.id,
                bio: bio,
                email: state.user.user.email,
                pic_url: state.user.user.profilePic
            });

            let user = {
                username: state.user.user.username,
                id: state.user.user.id,
                bio: bio,
                email: state.user.user.email,
                profilePic: state.user.user.profilePic
            };

            getUser(user);
        }
    };

    return (
        <div className="edit">
            <input type="file" accept="image/*" onChange={changeImage} />

            <h5>Edit Bio</h5>
            <input type="text" onChange={changeBio} />

            <br />
            <button onClick={upload}>Upload</button>
        </div>
    );
}