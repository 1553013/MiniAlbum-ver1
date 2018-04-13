# Mini Album ver 1.0

An album application manages images in Android device ver 5.0 or above. It allows us to interact with each image as viewing image in full screen mode, editing image with some basic tools, protecting your album through album's security and face recognition.

## Screenshots
<p align="center">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722419-164162ae-3f28-11e8-89a9-8334b980de58.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722420-1672691c-3f28-11e8-9ab1-3adf9e0bab18.png">
<img width="150px" src="https://user-images.githubusercontent.com/26139791/38722421-16a4cc9a-3f28-11e8-8a07-7919deffda92.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722422-16d7d5ae-3f28-11e8-91c8-685b5c6bfcf1.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722423-17547dca-3f28-11e8-914b-d0e0065968b0.png">
</p>
<p align="center">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722424-1788ced6-3f28-11e8-92e6-0800be8a1bee.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722426-17c13122-3f28-11e8-8300-84c74e81cb56.png">
<img width="150px" src="https://user-images.githubusercontent.com/26139791/38722428-183a2320-3f28-11e8-80b8-c0603d9b4e79.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722429-186cfc50-3f28-11e8-8ce6-eff752a24d94.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722430-18a18d4e-3f28-11e8-92c0-e7a2b4e159c4.png">
</p>
<p align="center">
<img width="150px" src="https://user-images.githubusercontent.com/26139791/38722431-18d5f2dc-3f28-11e8-929d-9023c3d923f7.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722432-19073234-3f28-11e8-91a8-35d3c97037a4.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722433-1938d00a-3f28-11e8-9b61-69ca1694b36f.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722434-1968a9c4-3f28-11e8-8aaf-d08450942d72.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722435-19973366-3f28-11e8-8b21-1c0fe6644b27.png">
</p>
<p align="center">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722436-19cafad4-3f28-11e8-934d-d48ccd5236cc.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722438-1a287a88-3f28-11e8-9d02-6e9efd7d6823.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38727860-61aed2b6-3f38-11e8-95cd-f4f4e24dd78c.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722440-1a8fa528-3f28-11e8-86d9-40137a913364.png">
<img  width="150px" src="https://user-images.githubusercontent.com/26139791/38722441-1ac010be-3f28-11e8-8180-8101406189bd.png">
</p>

## Install

<ul>
  <li>Method 1: Clone the project and build it by Android studio.</li>
  <li>Method 2: Download <a href="https://mega.nz/#F!Vqx1TDaC" >here</a> and install it through "Unknown sources".</li>
</ul>
<p><b>Note: </b>Just only support android device version is 5.0.0 or above.</p>

## Features:

<ul>
  <li>Grid view of images for general wacthing.</li>
  <li>Manage our images by each tab: Picture, Folder and Favorite.</li>
  <li>View each image in full screen mode.</li>
  <li>Set an image as wallpaper or sreen lock.</li>
  <li>Zoom in/out and roate an image.</li>
  <li>View details of each image(size, date taken, date modified, path, title,..).</li>
  <li>Share images to social networks as Facebook,...</li>
  <li>Album security.</li>
  <li>Some basic editor image tools as crop, brightness, contrast, effects</li>
  <li>Face recognition allows us to identify faces and save them. In addition, we can use them for changing avatar on social networks as Facebook.<li>
</ul>

## Development Environment
<ul>
  <li>Compatible with Android 5.0 and above</li>
  <li>App written in Java.</li>
  <li>Tools in use:
    <ul>
      <li>Android Studio 3.0.1</li>
      <li>Adobe Photoshop CS6: Design interface and icon.</li>
    </ul>
  </li>
</ul>

## Some Techniques

<ul>
  <li>Store information of each image by SqLite.</li>
  <li>Use ViewPager to manage fragments of each tab.</li>
  <li>Content Provider and Content Resolver are used for querying data from Database of android device.</li>
  <li>Handle CursorLoader flexibly.</li>
  <li>Apply ViewHolder design pattern for better caching and increase performance while loading images.</li>
  <li>Apply AsyncTask for breaking into small threads run parallel with Main Thread to prevent ANR( App Not Responding).</li>
  <li>Handle editing images with OpenCv Lib.</li>
  <li>Use Google Vision Api for regconize faces from images.</li>
</ul>

## External Libraries and Frameworks

<ul>
  <li><a href="https://github.com/bumptech/glide">Glide</a></li>
  <li><a href="https://github.com/chrisbanes/PhotoView">PhotoView</a></li>
  <li><a href="https://github.com/ArthurHub/Android-Image-Cropper">Image Cropper</a></li>
  <li><a href="https://blog.openshift.com/day-12-opencv-face-detection-for-java-developers/">OpenCv</a></li>
  <li><a href="https://developers.google.com/vision/">Google Vision</a></li>
</ul>

## Authors
<ul>
  <li>Khanh Huynh - Analyst, Design Architect, Developer- <b>Junior at Software Engineering, University of Science, HCM, Vietnam</b>
  </li>
  <li>Du Nguyen - Security, Design Architect, Developer- <b>Junior at Software Engineering, University of Science, HCM, Vietnam</b>
  </li>
</ul>

## Acknowledgements:

<ul>
  <li>Thanks <b>Mr. Ho Tuan Thanh</b> for guiding us through this project.</li>
  <li>Thanks <b>Google Vision</b> for wonderful tools to recognize people's faces.</li>
  <li>Thanks <b>Facebook for Developer </b> for sharing images and inspiring our app on it.</li>
</ul>

## Contributing

Your pull request for learning is welcome. If you get some issues, please contact me: <a href="mailto:khanh28197@gmail.com"><i>khanh28197@gmail.com</i> </a>

