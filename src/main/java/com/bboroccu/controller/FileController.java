package com.bboroccu.controller;

import com.bboroccu.model.dao.SongInfo;
import com.bboroccu.model.dao.SongInfoRepo;
import com.bboroccu.model.dao.SongRating;
import com.bboroccu.model.dao.SongRatingRepo;
import com.bboroccu.util.Mp3Header;
import com.bboroccu.util.Utility;
import com.mpatric.mp3agic.Mp3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import static com.bboroccu.util.Utility.getBytesFromFile;

/**
 * Created by bboroccu on 16. 1. 20..
 */
@RestController
@RequestMapping("/file")
public class FileController extends CommonController{
    @Autowired
    private SongInfoRepo songInfoRepo;
    @Autowired
    private SongRatingRepo songRatingRepo;

    @Value("${savefilepath}")
    String savePath;
    @Value("${mp3savepath}")
    String mp3Path;
    @Value("${imagesavepath}")
    String imagePath;

    /**
     *  음원 파일 저장
     * @param multipartFile
     * @param eMail
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveFile", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveFile(@RequestParam(value = "file", required = true) MultipartFile multipartFile,
                    @RequestParam(value = "email", required = true) String eMail) throws Exception {
        if(multipartFile.isEmpty() == false) {
            logger.info("------------- file start -------------");
            logger.info("name : " + multipartFile.getName());
            logger.info("filename : " + multipartFile.getOriginalFilename());
            logger.info("size : " + multipartFile.getSize());
            logger.info("-------------- file end --------------\n");
            String filePath = savePath + File.separator + mp3Path + File.separator + multipartFile.getOriginalFilename();
            File folder = new File(savePath);
            if (!folder.exists())
                folder.mkdir();
            folder = new File(savePath + File.separator + mp3Path);
            if (!folder.exists())
                folder.mkdir();
            File file = new File(filePath);
            if (file.exists())
                return Utility.getRtnMsg(0, "File Exist");
            try {
                multipartFile.transferTo(file);
                String title = "";
                String creator = "";
                try {
                    Mp3File mp3file = new Mp3File(file.getAbsoluteFile());
                    if (mp3file.hasId3v2Tag()) {
                        title = Mp3Header.LocalString(mp3file.getId3v2Tag().getTitle().getBytes("ISO-8859-1"));
                        creator = Mp3Header.LocalString(mp3file.getId3v2Tag().getArtist().getBytes("ISO-8859-1"));
                    } else {
                        title = Mp3Header.LocalString(mp3file.getId3v1Tag().getTitle().getBytes("ISO-8859-1"));
                        creator = Mp3Header.LocalString(mp3file.getId3v1Tag().getArtist().getBytes("ISO-8859-1"));
                    }
                    logger.info(mp3file.getId3v2Tag().getAlbumImageMimeType());
                    String imgName = multipartFile.getOriginalFilename();
                    imgName = imgName.substring(0, imgName.lastIndexOf("."));
                    String dbImageName = imgName + ".jpg";
                    String imgPath = savePath + File.separator + imagePath + File.separator + dbImageName;
                    folder = new File(savePath + File.separator + imagePath);
                    if (!folder.exists())
                        folder.mkdir();
                    SongInfo songInfo = new SongInfo();
                    songInfo.setTitle(title);
                    songInfo.setFilename(multipartFile.getOriginalFilename());
                    songInfo.setCreator(creator);
                    songInfo.setEmail(eMail);
                    songInfo.setPath(filePath);
                    songInfo.setImgpath(dbImageName);
                    if (songInfoRepo.findSongInfoToInfo(songInfo.getCreator(), songInfo.getTitle(), songInfo.getFilename()) != null)
                        return Utility.getRtnMsg(0, "MP3 Exist");
                    else {
                        InputStream in = new ByteArrayInputStream(mp3file.getId3v2Tag().getAlbumImage());
                        BufferedImage image = ImageIO.read(in);
                        ImageIO.write(image, "jpg", new File(imgPath));
                        songInfoRepo.save(songInfo);
                        SongInfo info = songInfoRepo.findSongInfoToInfo(songInfo.getCreator(), songInfo.getTitle(), songInfo.getFilename());
                        SongRating songRating = new SongRating();
                        songRating.setSongseq(info.getSeq());
                        songRatingRepo.save(songRating);
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    return Utility.getRtnMsg(0, ex.getMessage());
                }
            } catch (IOException iox) {
                logger.error(iox.getMessage());
                return Utility.getRtnMsg(0, iox.getMessage());
            }
            return Utility.getRtnMsg(1, "SUCCESS");
        } else
            return Utility.getRtnMsg(0, "Empty File");
    }

    /**
     * 음원파일 다운로드
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String seq = request.getParameter("seq");
        String originFileName = request.getParameter("fileName");
        String serverFileName = request.getParameter("serverFileName");
        byte fileByte[] = getBytesFromFile(new File(serverFileName));
        response.setContentType("application/octet_stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originFileName, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
        SongInfo songInfo = songInfoRepo.findOne(Integer.parseInt(seq));
        if (songInfo != null) {

            songInfoRepo.save(songInfo);
        }
    }
}
