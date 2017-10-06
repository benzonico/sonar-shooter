window.registerExtension('shooterplugin/loader', function (options) {
      var iframe = document.createElement('iframe');
      iframe.textContent = 'This does not work as expected';
      iframe.src = '../../../static/shooterplugin/sonarshooter-webgl/index.html';
      iframe.width = '600px';
      iframe.height = '800px';
      // append just created element to the container
      options.el.appendChild(iframe);
  // return a function, which is called when the page is being closed
  return function () {
  options.el.textContent = ' ';
  };
});
