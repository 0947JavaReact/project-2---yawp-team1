import * as React from 'react';
//import * as S3FileUpload from 'react-s3'

export const EditProfileForm: React.FC<any> = () => {

    let [img,setImg] = React.useState<any>()

    const config = {

        bucketName:"robertsrevbucket",
        dirName:"/pics",
        region:"us-west-1",
        accessKeyId:"AKIAUQCHGIORLTJBOA7I",
        secretAccessKey:"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",

    }

    function getImage(e:any){

        setImg(URL.createObjectURL(e.target.files[0]))

    } 

    return(
        <div className="edit">
            <input type="file" accept="image/*" onChange={getImage}></input>
            <h5>Edit Bio</h5>
            <input type="text"></input>
            <button></button>
        </div>
    )
}