$(document).ready(function(){
    var fileTarget = $('.filebox .upload-hidden');
    var index = 0;

    fileTarget.on('change', function(){
        if(window.FileReader){
            // 파일명 추출
            for(index = 0; index < $(this).length; index++){
                var filename = $(this)[index].files[index].name;
                $(this).siblings('.upload-name').val(filename);
            }
        }

        else {
            // Old IE 파일명 추출
            var filename = $(this).val().split('/').pop().split('\\').pop();
            $(this).siblings('.upload-name').val(filename);
        };
    });

    //preview image
    var imgTarget = $('.preview-image .upload-hidden');

    imgTarget.on('change', function(){
        var parent = $(this).parent();
        parent.children('.upload-display').remove();

        if(window.FileReader){
            //image 파일만
            if (!$(this)[0].files[0].type.match(/image\//)) return;

            var reader = new FileReader();
            reader.onload = function(e){
                var src = e.target.result;
                parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
            }
            reader.readAsDataURL($(this)[0].files[0]);
        }

        else {
            $(this)[0].select();
            $(this)[0].blur();
            var imgSrc = document.selection.createRange().text;
            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');

            var img = $(this).siblings('.upload-display').find('img');
            img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";
        }
    });
});