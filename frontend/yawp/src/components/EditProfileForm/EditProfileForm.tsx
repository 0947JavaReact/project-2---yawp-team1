import * as React from 'react';
import { useSelector } from 'react-redux';
import AWS from 'aws-sdk';

export const EditProfileForm: React.FC<any> = () => {
    let [img, setImg] = React.useState<any>()
    const state = useSelector<any, any>((state) => state);

    // const config = {
    //     bucketName: "robertsrevbucket",
    //     dirName: "/pics",
    //     region: "us-west-1",
    //     accessKeyId: "AKIAUQCHGIORLTJBOA7I",
    //     secretAccessKey: "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",
    // }

    AWS.config.update({
        accessKeyId: "AKIAUQCHGIORLTJBOA7I",
        secretAccessKey: "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",
        region: "us-west-1"
    });

    const s3 = new AWS.S3();

    function getImage(e: any) {
        // setImg(URL.createObjectURL(e.target.files[0]))
        setImg(e.target.files[0]);
    }

    const upload = (e: any) => {
        try {
            const extension = img.type.split("/")[1];
            const params = {
                Bucket: "robertsrevbucket",
                Key: `${state.user.user.username}-profile-picture.${extension}`,
                ContentType: img.type,
                Body: img
            };

            console.log(img);
            console.log(img.type);

            const res = s3.putObject(params).promise()
            .then(() => {
                const url = s3.getSignedUrl("getObject", {
                    Bucket: "robertsrevbucket",
                    Key: `${state.user.user.username}-profile-picture.${extension}`,
                    Expires: 60
                });

                console.log(url);
            });
        } catch (error) {
            console.log(error);
            return;
        }
    };

    return (
        <div className="edit">
            <input type="file" accept="image/*" onChange={getImage}></input>
            {/* <img src={URL.createObjectURL(img)} alt="I am an image." /> */}
            <h5>Edit Bio</h5>
            <input type="text"></input>
            <button onClick={upload}>Upload</button>
        </div>
    );
}