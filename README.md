
Add these lines to AndroidManifest.xml

    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />

Goes in between the application tags

    <service android:name="com.tesseractmobile.easysphero.SpheroService"></service>
